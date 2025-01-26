import { configureStore } from "@reduxjs/toolkit"
import { selectedTaskReducer, tasksReducer } from "../features/tasks/taskSlice"
import paginationReducer from "../features/tasks/paginationSlice"
import filterReducer from "../features/forms/filterSlice"
import { createModalReducer, updateModalReducer } from "../features/forms/modalSlice"
import statReducer from "../features/stats/statSlice"

export const store = configureStore({
    reducer: {
        tasks: tasksReducer,
        pagination: paginationReducer,
        filters: filterReducer,
        creation: createModalReducer,
        update: updateModalReducer,
        selectedTask: selectedTaskReducer,
        stats: statReducer
    }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
export type AppStore = typeof store