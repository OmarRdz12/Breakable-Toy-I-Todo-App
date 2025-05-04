import { createSlice } from "@reduxjs/toolkit";

export const pendingSlice = createSlice({
    name: 'pending',
    initialState: 0,
    reducers: {
        updatePending: (state, action) => {
           return action.payload
        },
    }
})

export const { updatePending } = pendingSlice.actions
export const pendingReducer =  pendingSlice.reducer