const path = require('path');
const paths = require('./config/paths');
const webpack = require('webpack');


module.exports = {
    mode: 'production',
    entry: path.join(paths.appSrc, 'index.js'),
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