const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const port = 3000;

app.use(bodyParser.json());

let items = [];

// Create
app.post('/items', (req, res) => {
    const item = req.body;
    item.id = items.length + 1;
    items.push(item);
    res.status(201).json(item);
});

// Read (all)
app.get('/items', (req, res) => {
    res.json(items);
});

// Read (one)
app.get('/items/:id', (req, res) => {
    const item = items.find(i => i.id === parseInt(req.params.id));
    if (item) {
        res.json(item);
    } else {
        res.status(404).send('Item not found');
    }
});

// Update
app.put('/items/:id', (req, res) => {
    const index = items.findIndex(i => i.id === parseInt(req.params.id));
    if (index !== -1) {
        items[index] = { ...items[index], ...req.body, id: items[index].id };
        res.json(items[index]);
    } else {
        res.status(404).send('Item not found');
    }
});

// Delete
app.delete('/items/:id', (req, res) => {
    const index = items.findIndex(i => i.id === parseInt(req.params.id));
    if (index !== -1) {
        items.splice(index, 1);
        res.status(204).send();
    } else {
        res.status(404).send('Item not found');
    }
});

app.listen(port, () => {
    console.log(`API running on port ${port}`);
});
