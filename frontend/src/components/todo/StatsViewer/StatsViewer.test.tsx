import { render, screen } from "@testing-library/react"
import "@testing-library/react"
import { Provider } from "react-redux"
import StatsViewer from "./StatsViewer"
import { store } from "../../../app/store"
import statReducer from "../../../features/stats/statSlice"
import { configureStore } from "@reduxjs/toolkit"

describe("Stats section", () => {
    vi.stubGlobal("matchMedia", vi.fn(() => ({
        matches: false,
        addListener: vi.fn(),
        removeListener: vi.fn()
    })))

    test("should render section stats", () => {
        render(
            <Provider store={store} >
                <StatsViewer />
            </Provider>
        )
        expect(screen.queryByText(/Average time to finish tasks by priority/)).not.toBeNull()
    })
})


test("should correct stats", () => {
    const customStore = configureStore({
        reducer: {
            stats: statReducer
        },
        preloadedState: {
            stats: {
                allDoneStats: "2:0:0",
                highDoneStats: "0:1:0",
                lowDoneStats: "00:00:00",
                mediumDoneStats: "0:10:0",
            }
        }
    }

    )
    render(
        <Provider store={customStore} >
            <StatsViewer />
        </Provider>
    )
    
    expect(screen.queryByText(/High: 0 hours, 1 minutes and 0 seconds/)).not.toBeNull()
    expect(screen.queryByText(/Medium: 0 hours, 10 minutes and 0 seconds/)).not.toBeNull()
    expect(screen.getByText(/Low: There are no statistics to show/)).not.toBeNull()
})
