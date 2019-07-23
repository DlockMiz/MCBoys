const prod = {
    url:{
        WEBSOCKET_URL: "http://67.205.128.141:8080"        
    }
}

const dev = {
    url:{
        WEBSOCKET_URL: "http://localhost:8080"        
    }
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod