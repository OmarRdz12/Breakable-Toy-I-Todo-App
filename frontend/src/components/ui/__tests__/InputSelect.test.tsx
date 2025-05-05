import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import InputSelect from '../InputSelect'
import { within } from '@testing-library/react'

describe('InputSelect', () => {
    const defaultProps = {
        id: 'country',
        label: 'Country',
        name: 'country',
        required: false,
        onChange: vi.fn(),
        options: [
            { label: 'USA', value: 'us' },
            { label: 'Canada', value: 'ca' },
        ],
        defaultValue: 'us',
    }

    beforeEach(() => {
        defaultProps.onChange.mockClear()
    })

    it('renders the label and select', () => {
        render(<InputSelect {...defaultProps} />)

        expect(screen.getByText('Country')).toBeInTheDocument()
        expect(screen.getByRole('combobox')).toBeInTheDocument()
    })

    it('displays an asterisk when required is true', () => {
        render(<InputSelect {...defaultProps} required={true} />)

        expect(screen.getByText('*')).toBeInTheDocument()
    })

    it('shows the default selected value', () => {
        render(<InputSelect {...defaultProps} />)

        expect(screen.getByText('USA')).toBeInTheDocument()
    })


    it('renders all options provided', async () => {
        const user = userEvent.setup()
        render(<InputSelect {...defaultProps} />)

        await user.click(screen.getByRole('combobox'))

        const dropdown = document.querySelector('.ant-select-dropdown')
        expect(dropdown).not.toBeNull()

        const dropdownUtils = within(dropdown as HTMLElement)

        expect(dropdownUtils.getByText('USA')).toBeInTheDocument()
        expect(dropdownUtils.getByText('Canada')).toBeInTheDocument()
    })

    it('calls onChange when an option is selected', async () => {
        const user = userEvent.setup()
        render(<InputSelect {...defaultProps} />)

        const select = screen.getByRole('combobox')
        await user.click(select)

        const option = await screen.findByText('Canada')
        await user.click(option)

        expect(defaultProps.onChange).toHaveBeenCalledWith('ca', expect.anything())
    })
})
