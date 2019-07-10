const path = require('path');
const paths = require('./config/paths');
const webpack = require('webpack');
const backend = "http://localhost:8080"

module.exports = {
    mode: 'development',
    devtool: 'sourcemaps',
    entry: path.join(paths.appSrc, 'index.js'),
    devServer: {
        contentBase: paths.appBuild,
        port: 8081,
        proxy: {
            "*": {
                "changeOrigin": true,
                "target": backend,
                onProxyReq: proxyReq => {
                    if (proxyReq.getHeader('origin')) {
                        proxyReq.setHeader('origin', backend);
                    }
                }
            }
        }
    },
    output: {
        path: paths.appBuild,
        filename: 'bundle.js',
        publicPath: '/'
    },
    cache: true,
    module: {
        rules: [
            {
                test: /\.js$/,
                loader: 'babel-loader',
                include: [
                     paths.appSrc
                ],
                exclude: [
                    /(node_modules)/
                ],
                options: {
                    presets: ['@babel/preset-react'],
                    plugins: [
                        "@babel/plugin-proposal-class-properties"
                    ]
                }
            },
            {
                test: /\.css$/,
                loader: 'style-loader!css-loader',
                include: [paths.appSrc, paths.appNodeModules]
            }
        ]
    },
    plugins: [
        new webpack.LoaderOptionsPlugin({
            debug: true
        })
    ]
};