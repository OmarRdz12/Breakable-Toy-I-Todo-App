
import { fireEvent, render, screen } from "@testing-library/react"
import BasePagination from "./Pagination"

describe("Pagination", () => {
    vi.stubGlobal("matchMedia", vi.fn(() => ({
        matches: false,
        addListener: vi.fn(),
        removeListener: vi.fn()
    })))

    const fn = vi.fn()

    test("should render pagination", () => {
        render(<BasePagination current={1} total={50} onChange={fn} />)
        expect(screen.getByText(/5/))
    })

    test("should update next page", () => {
        render(<BasePagination current={1} total={50} onChange={fn} />)
        const secondPage = screen.getByTitle(/2/)
        fireEvent.click(secondPage)
        expect(fn).toHaveBeenCalledWith(2,10)
    })

    test("should not render a next page if there is not enough data ", () => {
        render(<BasePagination current={1} total={5} onChange={fn} />)
        expect(screen.queryByText(/2/)).toBeNull()
    })


})
