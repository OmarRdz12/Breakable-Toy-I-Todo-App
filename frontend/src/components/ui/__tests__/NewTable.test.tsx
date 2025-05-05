import { render, screen } from '@testing-library/react'
import { configureStore } from '@reduxjs/toolkit'
import { Provider } from 'react-redux'
import NewTable from '../NewTable'
import { updateModalReducer } from '../../../features/forms/modalSlice'
import { sortReducer } from '../../../features/forms/filterSlice'
import { tasksReducer } from '../../../features/tasks/taskSlice'
import type { Task } from '../types'

const mockTasks: Task[] = [
    {
        id: 1,
        name: 'Urgent task',
        priority: 'High',
        dueDate: new Date(Date.now() + 2 * 24 * 60 * 60 * 1000).toISOString(),
        state: false,
        doneDate: ""
    },
    {
        id: 2,
        name: 'Future task',
        priority: 'Low',
        dueDate: new Date(Date.now() + 10 * 24 * 60 * 60 * 1000).toISOString(),
        state: true,
        doneDate: ""
    }
]

const headers = [
    { title: 'Task name' },
    { title: 'Priority' },
    { title: 'Due date' },
    { title: 'Actions' }
]

const fetchData = vi.fn()

function renderWithStore(updateOpen = false) {
    const store = configureStore({
        reducer: {
            update: updateModalReducer,
            sorts: sortReducer,
            tasks: tasksReducer,
        },
        preloadedState: {
            update: { open: updateOpen },
            sorts: { prioritySort: '', dueDateSort: '' },
            tasks: { data: [] }
        }
    })

    return render(
        <Provider store={store}>
            <NewTable headers={headers} rows={mockTasks} fetchData={fetchData} columnSelector={true} />
        </Provider>
    )
}

describe('NewTable', () => {
    it('renders headers and rows', () => {
        renderWithStore()

        expect(screen.getByText('Task name')).toBeInTheDocument()
        expect(screen.getAllByRole('row')).toHaveLength(mockTasks.length + 1)
    })

    it('renders column selector checkbox', () => {
        renderWithStore()

        const checkboxes = screen.getAllByRole('checkbox')
        expect(checkboxes.length).toBeGreaterThan(0)
    })

    it('disables edit and delete buttons for completed tasks', () => {
        renderWithStore()

        const rows = screen.getAllByRole('row')
        const secondRowButtons = rows[2].querySelectorAll('button')

        secondRowButtons.forEach((btn) => {
            expect(btn).toBeDisabled()
        })
    })

    it('applies correct background color based on due date', () => {
        renderWithStore()

        const urgentRow = screen.getByText('Urgent task').closest('tr')
        expect(urgentRow).toHaveClass('bg-red-200')

        const futureRow = screen.getByText('Future task').closest('tr')
        expect(futureRow).toHaveClass('bg-yellow-200')
    })
})
