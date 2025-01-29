import { createSlice, PayloadAction } from "@reduxjs/toolkit"

interface FilterState {
    name: string,
    state: string,
    priority: string,
}

interface SortState {
    dueDateSort: string,
    prioritySort: string
}

const initialState: FilterState = {
    name: "",
    state: "all",
    priority: "all",
}

const initialStateSort: SortState = {
    dueDateSort: "",
    prioritySort: ""
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

export const sortSlice = createSlice({
    name: 'sorts',
    initialState: initialStateSort,
    reducers: {
        onChangeSort: (state, action: PayloadAction<Partial<SortState>>) => {
            Object.assign(state, action.payload)
        }
    }
})



export const { onChange } = filterSlice.actions
export const filterReducer =  filterSlice.reducer
export const { onChangeSort } = sortSlice.actions
export const sortReducer = sortSlice.reducer