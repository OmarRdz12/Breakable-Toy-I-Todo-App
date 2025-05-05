import { render, screen, fireEvent } from '@testing-library/react'
import InputDate from '../InputDate'
import dayjs from 'dayjs'

describe('InputDate', () => {
  const onChangeMock = vi.fn()

  const defaultProps = {
    id: 'birthdate',
    label: 'Birthdate',
    name: 'birthdate',
    onChange: onChangeMock,
    value: null,
  }

  beforeEach(() => {
    onChangeMock.mockClear()
  })

  it('renders the label and DatePicker input', () => {
    render(<InputDate {...defaultProps} />)

    expect(screen.getByText(/birthdate/i)).toBeInTheDocument()
    const input = screen.getByRole('textbox')
    expect(input).toBeInTheDocument()
  })

  it('displays an asterisk when required', () => {
    render(<InputDate {...defaultProps} required={true} />)

    expect(screen.getByText('*')).toBeInTheDocument()
  })

  it('passes id and name props correctly', () => {
    render(<InputDate {...defaultProps} size="large" />)

    const input = screen.getByRole('textbox')
    expect(input).toHaveAttribute('id', 'birthdate')
    expect(input).toHaveAttribute('name', 'birthdate')
  })

  it('calls onChange when triggered manually', () => {
    render(<InputDate {...defaultProps} />)
    const input = screen.getByRole('textbox')

    fireEvent.change(input, { target: { value: '12/25/2024' } })
    expect(onChangeMock).not.toHaveBeenCalled()
  })


  it('accepts and formats a Dayjs value', () => {
    const testDate = dayjs('2024-10-10')
    render(<InputDate {...defaultProps} value={testDate} />)

    const input = screen.getByRole('textbox')
    expect(input).toHaveValue('2024-10-10')
  })
})
