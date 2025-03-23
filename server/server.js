import { PORT } from './constants.js';
import express from 'express';
import { createServer } from 'http';
import { Server } from 'socket.io';

const app = express();
const server = createServer(app);
const io = new Server(server);

const activeUsers = new Set();

io.on('connection', (client) => {
    console.log(`connection recieved`);    
    
    //listing to new chats
    client.on('new_message', (chat) => {
        console.log(`new message recieved: ${chat}`)
        client.broadcast.emit('broadcast', chat)
    })

    //listning to disconnet events
    client.on('disconnect', () => {
        console.log(`client disconnected: ${client.id}`);
    });
})

app.get('/', (req, res) => {
    res.send('Server is running')
});

console.log(PORT)
server.listen(PORT, () => {
    console.log(`server running at ${PORT}...`)
})