import { fireEvent, render, screen } from "@testing-library/react"
import BaseModal from "./Modal";
import "@testing-library/react"

describe("Modal", () => {
    const fn = vi.fn()
    const {getComputedStyle} = window
    window.getComputedStyle = (elt) => getComputedStyle(elt)

    test("should not render a button when openModal is false", () => {
        render(
            <BaseModal openModal={false} closeModal={() => {}} title="Test" onSubmit={fn}>
                <p>hola test</p>
            </BaseModal>
        )
        expect(screen.queryByText(/Test/)).toBeNull()
    })

    test("should render a button when openModal is true", () => {
        render(
            <BaseModal openModal={true} closeModal={() => {}} title="Test" onSubmit={fn}>
                <p>hola test</p>
            </BaseModal>
        )
        expect(screen.queryByText(/Test/)).not.toBeNull()
    })

    test("should render called on Submit when button is clicked", () => {
        render(
            <BaseModal openModal={true} closeModal={() => {}} title="Test" onSubmit={fn}>
                <p>hola test</p>
            </BaseModal>
        )
        const button = screen.getByText("OK")
        fireEvent.click(button)
        expect(fn).toBeCalledTimes(1)
    })
})
