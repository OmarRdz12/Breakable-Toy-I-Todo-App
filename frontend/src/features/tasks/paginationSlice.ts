import { createSlice } from "@reduxjs/toolkit"

interface PaginationState {
    current: number,
    total: number
}

const initialState: PaginationState = {
    current: 1,
    total: 0
}

export const paginationSlice = createSlice({
    name: 'pagination',
    initialState,
    reducers: {
        changePage: (state, action) => {
            state.current = action.payload
        },
        reloadRecords: (state, action) => {
            state.total = action.payload
        }
    }
})

export const { changePage, reloadRecords } = paginationSlice.actions
export default paginationSlice.reducer