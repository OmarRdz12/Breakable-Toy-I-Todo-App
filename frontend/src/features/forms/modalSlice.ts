import { createSlice } from "@reduxjs/toolkit"

interface ModalState {
    open: boolean
}

const initialState: ModalState = {
    open: false
}

export const createModalSlice = createSlice({
    name: 'createModal',
    initialState,
    reducers: {
        controlCreate: (state, action) => {
            state.open = action.payload
        }
    }
})

export const updateModalSlice = createSlice({
    name: 'updateModal',
    initialState,
    reducers: {
        controlUpdate: (state, action) => {
            state.open = action.payload
        }
    }
})

export const { controlCreate } = createModalSlice.actions
export const createModalReducer = createModalSlice.reducer

export const { controlUpdate } = updateModalSlice.actions
export const updateModalReducer = updateModalSlice.reducer