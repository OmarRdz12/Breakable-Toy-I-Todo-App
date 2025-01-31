import { render, screen } from "@testing-library/react"
import "@testing-library/react"
import "@testing-library/jest-dom"
import { Provider } from "react-redux"
import { store } from "../../../app/store"
import { configureStore } from "@reduxjs/toolkit"
import { tasksReducer } from "../../../features/tasks/taskSlice"
import paginationReducer from "../../../features/tasks/paginationSlice"
import { updateModalReducer } from "../../../features/forms/modalSlice"
import { sortReducer } from "../../../features/forms/filterSlice"
import DataViewer from "./DataViewer"

describe("Data section", () => {
    vi.stubGlobal("matchMedia", vi.fn(() => ({
        matches: false,
        addListener: vi.fn(),
        removeListener: vi.fn()
    })))

    test("should render table with headers", () => {
        render(
            <Provider store={store} >
                <DataViewer fetchData={async () => { }} />
            </Provider>
        )
        expect(screen.queryByText(/Name/)).not.toBeNull()
        expect(screen.queryByText(/Priority/)).not.toBeNull()
        expect(screen.queryByText(/Due Date/)).not.toBeNull()
        expect(screen.queryByText(/Actions/)).not.toBeNull()
    })

    test("should render table with tasks", () => {
        const customStore = configureStore({
            reducer: {
                tasks: tasksReducer,
                pagination: paginationReducer,
                update: updateModalReducer,
                sorts: sortReducer
            },
            preloadedState: {
                tasks: {
                    data: [
                        {
                            name: "test 1",
                            priority: "HIGH",
                            dueDate: "2025-01-31",
                            doneDate: "",
                            state: false,
                            id: 0
                        },
                        {
                            name: "test 2",
                            priority: "LOW",
                            dueDate: "2025-02-14",
                            doneDate: "",
                            state: false,
                            id: 1
                        },

                    ]
                }
            }

        })
        render(
            <Provider store={customStore} >
                <DataViewer fetchData={async () => { }} />
            </Provider>
        )
        expect(screen.queryByText(/test 2/)).not.toBeNull()
        expect(screen.queryByText(/test 1/)).not.toBeNull()
    })
})


