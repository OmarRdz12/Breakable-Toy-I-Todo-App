# Breakable-Toy-I-Todo-App (frontend)
## Description
The application interface is divided into several sections that function as components of the application.

- Filter section: Tasks can be filtered by their name or part of their name, by priority, and by the status they are in.
- Task section: Tasks in memory are displayed in table format, 10 at a time. They can be sorted by priority and/or due date. Edits and deletions can also be made in this section.
- Statistics section: This section shows the average time the user has from the moment a task is created until it is marked as completed.

### Technologies used
- Vite: is an application compilation tool. (https://vite.dev/)
- Antd: component library for react. (https://ant.design/)
- Axios: helps to make HTTP requests and handle responses. (https://axios-http.com/docs/intro)
- React-icons: Icon library for some sections (https://react-icons.github.io/react-icons/)
- Sonner: Library to render toasts to display messages (https://sonner.emilkowal.ski/)
- Redux Toolkit: Library to manage states between components. (https://redux-toolkit.js.org/)

## Run and test
1. Run :
``` bash
    npm install
    npm run dev
```
2. Test :
``` bash
    npm run test
```

## Ports used
- **Front-end:** `8080`
