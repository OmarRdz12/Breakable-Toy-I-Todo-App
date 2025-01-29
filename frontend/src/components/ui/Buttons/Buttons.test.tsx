import BaseButton from "./Buttons";
import { fireEvent, render, screen } from "@testing-library/react"

describe("Button", () => {

    const fn = vi.fn()
    beforeEach(() => {
        render(<BaseButton htmlType="button" text="test button" id="test" onClick={fn} />)
    })

    test("should render a button", () => {
        expect(screen.getByText("test button")).toBeDefined()
    })

    test("should call function when is clicked", () => {
        const button = screen.getByText("test button")
        fireEvent.click(button)
        expect(fn).toHaveBeenCalledTimes(1)
    })
})
