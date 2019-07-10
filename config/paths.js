const path = require('path');

function resolve(relativePath) {
    return path.resolve(__dirname, relativePath);
}

module.exports = {
    appSrc: resolve('../src/main/js'),
    appBuild: resolve('../build/resources/main/static'),
    appNodeModules: resolve('../node_modules')
};