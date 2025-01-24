import { createSlice } from "@reduxjs/toolkit"
import { Task } from "../../components/ui/types"
import { format } from "date-fns"

interface Tasks {
    data: Task[]
}

const initialState: Tasks = {
    data: []
}

const initialStateSelected: Task  = {
    name: "",
    priority: "",
    dueDate: "",
    doneDate: "",
    state: false,
    id: 0
}

export const taskSlice = createSlice({
    name: 'tasks',
    initialState,
    reducers: {
        updateRecords: (state, action) => {
            const list: Task[] = action.payload
            list.forEach((element: Task) => {
                element.key = element.id
            })
            state.data = list
        }
    }
})

export const selectedTaskSlice = createSlice({
    name: 'selectedTask',
    initialState: initialStateSelected,
    reducers: {
        updateSelected: (state, action) => {
            state.name = action.payload.name
            state.priority = action.payload.priority
            state.dueDate = action.payload.dueDate
            state.id = action.payload.id
        }
    }
})

export const { updateRecords } = taskSlice.actions
export const tasksReducer = taskSlice.reducer
export const { updateSelected } = selectedTaskSlice.actions
export const selectedTaskReducer = selectedTaskSlice.reducer
