const fs = require('fs');
const xml2js = require('xml2js');
const glob = require('glob');
const util = require('util');
const parser = new xml2js.Parser();
const parseStringAsync = util.promisify(parser.parseString.bind(parser));

const maxEntries = process.env.MAX_ENTRIES ? parseInt(process.env.MAX_ENTRIES, 10) : 20;
const xmlPattern = process.env.XML_PATTERN || 'build/test-results/test/**/*.xml';

async function processFile(file) {
    const data = fs.readFileSync(file);
    const result = await parseStringAsync(data);

    const testCases = result.testsuite.testcase;
    const failedTests = testCases.filter(test => test.failure);
    const skippedTests = testCases.filter(test => test.skipped);
    const successfulTests = testCases.length - failedTests.length - skippedTests.length;

    const failuresDetails = failedTests.map(test => {
        const time = parseFloat(test.$.time);
        const timeFormatted = time >= 60 ?
            `${Math.floor(time / 60)} min ${Math.round(time % 60)} sec`
            : `${time.toFixed(2)} sec`;
        return { name: `${test.$.classname} - ${test.$.name}`, time: timeFormatted };
    });

    return {
        totalCases: testCases.length,
        successful: successfulTests,
        failed: failedTests.length,
        skipped: skippedTests.length,
        details: failuresDetails
    };
}

async function summarizeResults() {
    const files = glob.sync(xmlPattern);
    const results = await Promise.all(files.map(file => processFile(file)));

    const totalSummary = results.reduce((acc, curr) => ({
        totalCases: acc.totalCases + curr.totalCases,
        totalSuccessful: acc.totalSuccessful + curr.successful,
        totalFailed: acc.totalFailed + curr.failed,
        totalSkipped: acc.totalSkipped + curr.skipped,
        detailedFailures: acc.detailedFailures.concat(curr.details)
    }), {
        totalCases: 0,
        totalSuccessful: 0,
        totalFailed: 0,
        totalSkipped: 0,
        detailedFailures: []
    });

    totalSummary.detailedFailures = totalSummary.detailedFailures.slice(0, maxEntries);
    totalSummary.status = totalSummary.totalFailed > 0 ? 'Failure' : 'Success';

    console.log(JSON.stringify(totalSummary));
}

summarizeResults();
