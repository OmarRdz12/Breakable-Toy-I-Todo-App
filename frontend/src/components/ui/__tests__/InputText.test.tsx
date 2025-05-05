import { render, screen, fireEvent } from '@testing-library/react'
import InputText from '../InputText'

describe('InputText', () => {
  const defaultProps = {
    name: 'email',
    placeholder: 'Enter your email',
    id: 'email-input',
    value: '',
    onChange: vi.fn(),
    label: 'Email',
    type: 'text',
  }

  it('renders the label and input field', () => {
    render(<InputText {...defaultProps} />)

    expect(screen.getByLabelText(/email/i)).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Enter your email')).toBeInTheDocument()
  })

  it('displays an asterisk when the field is required', () => {
    render(<InputText {...defaultProps} required={true} />)

    expect(screen.getByText('*')).toBeInTheDocument()
  })

  it('calls onChange when the input value changes', () => {
    render(<InputText {...defaultProps} />)
    const input = screen.getByPlaceholderText('Enter your email')

    fireEvent.change(input, { target: { value: 'test@example.com' } })

    expect(defaultProps.onChange).toHaveBeenCalled()
  })

  it('applies props like maxLength, showCount, and type correctly', () => {
    render(<InputText {...defaultProps} maxLength={10} showCount={true} type="text" />)
    const input = screen.getByPlaceholderText('Enter your email') as HTMLInputElement

    expect(input).toHaveAttribute('maxlength', '10')
    expect(input).toHaveAttribute('type', 'text')
  })

  it('associates the label with the input using htmlFor and id', () => {
    render(<InputText {...defaultProps} />)
    const label = screen.getByText(/email/i)
    expect(label).toHaveAttribute('for', 'email-input')
  })
})
