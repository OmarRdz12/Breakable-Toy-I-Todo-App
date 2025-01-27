import { createSlice } from "@reduxjs/toolkit"
import { Task } from "../../components/ui/types"

interface Tasks {
    data: Task[]
}

const initialState: Tasks = {
    data: []
}

const initialStateSelected: Task = {
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

export const stateTaskSlice = createSlice({
    name: 'stateTask',
    initialState: false,
    reducers: {
        stateRecords: (state, action) => {
            const list: Task[] = action.payload
            state = list.every((item: Task) => item.state === true)
            return state
        },
        updateStateRecords: (state, action) => {
            (state: boolean) => !state
            return state
        }
    }
})

export const { updateRecords } = taskSlice.actions
export const tasksReducer = taskSlice.reducer
export const { updateSelected } = selectedTaskSlice.actions
export const selectedTaskReducer = selectedTaskSlice.reducer
export const { stateRecords, updateStateRecords } = stateTaskSlice.actions
export const stateTaskReducer = stateTaskSlice.reducer
