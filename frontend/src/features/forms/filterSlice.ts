import { createSlice, PayloadAction } from "@reduxjs/toolkit"

interface FilterState {
    name: string,
    state: string,
    priority: string
}

const initialState: FilterState = {
    name: "",
    state: "all",
    priority: "all"
}

export const filterSlice = createSlice({
    name: 'filters',
    initialState,
    reducers: {
        onChange: (state, action: PayloadAction<Partial<FilterState>>) => {
            Object.assign(state, action.payload)
        }
    }
})

export const { onChange } = filterSlice.actions
export default filterSlice.reducer