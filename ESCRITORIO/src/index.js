const { app, BrowserWindow, Menu} = require('electron');
const path = require('path');
const url = require('url');
require('electron-reload')(__dirname);

if (require('electron-squirrel-startup')) {
    app.quit();
}

let mainWindow;

app.on('ready', () =>{
    mainWindow = new BrowserWindow({
        frame:false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        webPreferences: {
            nodeIntegration: true
        }
    });
    mainWindow.loadURL(url.format({
        pathname: path.join(__dirname, 'views/index.html'),
        protocol: 'file',
        slashes: true,
    }));

    const mainMenu = Menu.buildFromTemplate(templateMenu);
    Menu.setApplicationMenu(mainMenu);

    mainWindow.on('closed', () =>{
        app.quit();
    })
    
    mainWindow.maximize();
    mainWindow.on('unmaximize', () => mainWindow.maximize());
});

app.on("window-all-closed", () => {
  if (process.platform !== "darwin") {
    app.quit();
  }
});

const templateMenu = [
    {
        label: 'File',
        submenu: [
            {
                label: 'New Product',
                accelerator: 'Ctrl+N',
                click(){
                    createNewProductWindow();
                }
            },
            {
                label: 'Remove all products',
                click(){

                }
            },
            {
                label: 'Exit',
                accelerator: process.platform == 'darwin' ? 'command+Q' : 'Ctrl+Q',
                click(){
                    app.quit();
                }
            }
        ]
    },
    
];

if(process.platform == 'darwin'){
    templateMenu.unshift({
        label: app.getName()
    });
}
if(process.env.NODE_ENV !== 'production'){
    templateMenu.push({
        label: 'DevTools',
        submenu: [
            {
                label: 'Show/Hide Dev Tools',
                click(item, focusedWindow){
                    focusedWindow.toggleDevTools();
                },
                accelerator: 'Ctrl+D'
            },
            {
                role: 'reload'
            }
        ]
    });
}