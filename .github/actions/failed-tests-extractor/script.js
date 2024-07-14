const fs = require('fs');
const xml2js = require('xml2js');
const glob = require('glob');
const parser = new xml2js.Parser();

const maxEntries = process.env.MAX_ENTRIES ? parseInt(process.env.MAX_ENTRIES, 10) : 20;
const xmlPattern = process.env.XML_PATTERN || 'build/reports/tests/**/TEST-*.xml';

glob(xmlPattern, (err, files) => {
    if (err) throw err;
    files.forEach(file => {
        fs.readFile(file, (err, data) => {
            if (err) throw err;
            parser.parseString(data, (err, result) => {
                if (err) throw err;
                const testCases = result.testsuite.testcase;
                const failedTests = testCases.filter(test => test.failure);

                const summary = failedTests.slice(0, maxEntries).map(test => ({
                    name: test.$.name,
                    time: test.$.time
                }));

                console.log(JSON.stringify(summary));  // JSON形式で出力
            });
        });
    });
});
