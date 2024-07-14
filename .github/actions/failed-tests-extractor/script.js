const fs = require('fs');
const path = require('path');
const xml2js = require('xml2js');
const glob = require('glob');
const parser = new xml2js.Parser();

const maxEntries = process.env.MAX_ENTRIES ? parseInt(process.env.MAX_ENTRIES, 10) : 20;
const xmlPattern = process.env.XML_PATTERN || 'build/test-reports/test/**/*.xml';

// ディレクトリを再帰的に走査し、ファイル一覧を出力する関数
function listFiles(dir, baseDir = '') {
    const fullPath = path.join(baseDir, dir);
    console.log(`Listing files in directory: ${fullPath}`);
    try {
        const files = fs.readdirSync(fullPath, { withFileTypes: true });
        files.forEach(file => {
            if (file.isDirectory()) {
                listFiles(file.name, fullPath);
            } else {
                console.log(`File: ${path.join(fullPath, file.name)}`);
            }
        });
    } catch (err) {
        console.error(`Error accessing directory: ${fullPath}`, err);
    }
}

// ビルドディレクトリのファイル一覧をログに出力
console.log('Starting to list all files under the build directory');
listFiles('build');

console.log(`Starting to process XML files with pattern: ${xmlPattern}`);
console.log(`Maximum entries to report: ${maxEntries}`);

glob(xmlPattern, (err, files) => {
    if (err) {
        console.error(`Error finding files with glob pattern: ${xmlPattern}`, err);
        throw err;
    }

    console.log(`Found ${files.length} files`);

    if (files.length === 0) {
        console.log('No XML files found. Exiting.');
        return;
    }

    files.forEach((file, index) => {
        console.log(`Reading file ${index + 1}: ${file}`);
        fs.readFile(file, (err, data) => {
            if (err) {
                console.error(`Error reading file: ${file}`, err);
                throw err;
            }

            parser.parseString(data, (err, result) => {
                if (err) {
                    console.error(`Error parsing XML data from file: ${file}`, err);
                    throw err;
                }

                if (!result.testsuite || !result.testsuite.testcase) {
                    console.log(`No test cases found in file: ${file}`);
                    return;
                }

                const testCases = result.testsuite.testcase;
                const failedTests = testCases.filter(test => test.failure);

                if (failedTests.length === 0) {
                    console.log(`No failed tests in file: ${file}`);
                } else {
                    console.log(`Found ${failedTests.length} failed tests in file: ${file}`);
                }

                const summary = failedTests.slice(0, maxEntries).map(test => ({
                    name: `${test.$.classname} - ${test.$.name}`,
                    time: `${Math.floor(test.$.time / 60000)}m ${Math.ceil((test.$.time % 60000) / 100) / 10}s`,
                }));

                console.log(`Reporting summary for file: ${file}`);
                console.log(JSON.stringify(summary));  // JSON形式で出力
            });
        });
    });
});
