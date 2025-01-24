import { createSlice } from "@reduxjs/toolkit"
import { Task } from "../../components/ui/types"
import { format } from "date-fns"

interface Tasks {
    data: Task[]
}

const initialState: Tasks = {
    data: []
}

export const taskSlice = createSlice({
    name: 'tasks',
    initialState,
    reducers: {
        updateRecords: (state, action) => {
            const list: Task[] = action.payload
            list.forEach((element: Task) => {
                element.key = element.id
                element.dueDate = format(element.dueDate, 'MM/dd/yyyy');
            })
            state.data = list
        }
    }
})

export const { updateRecords } = taskSlice.actions
export default taskSlice.reducer