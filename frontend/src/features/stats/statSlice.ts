import { createSlice } from "@reduxjs/toolkit"

interface StatsProps {
    allDoneStats: string
    highDoneStats: string
    lowDoneStats: string
    mediumDoneStats: string
}

const initialState: StatsProps = {
    allDoneStats: "0:0:0",
    highDoneStats: "0:0:0",
    lowDoneStats: "0:0:0",
    mediumDoneStats: "0:0:0",
}

export const statSlice = createSlice({
    name: 'stats',
    initialState,
    reducers: {
        updateStats: (state, action) => {
            state.allDoneStats = action.payload.allDoneStats
            state.highDoneStats = action.payload.highDoneStats
            state.mediumDoneStats = action.payload.mediumDoneStats
            state.lowDoneStats = action.payload.lowDoneStats
        },
    }
})

export const { updateStats } = statSlice.actions
export default statSlice.reducer