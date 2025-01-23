import { SizeType } from "antd/es/config-provider/SizeContext"

export interface BaseInputProps {
    name: string
    placeholder?: string
    id: string
    value?: string
    className?: string
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void 
    size?: SizeType
    prefix?: React.ReactNode
    type: string
    label: string
    required?: boolean
    maxLength?: number
    showCount?: boolean
    variant?: "outlined" | "borderless" | "filled" | undefined
}

interface Choice {
    value: string | number 
    label: string
}

export interface SelectInputProps {
    options: Choice[]
    defaultValue?: string
    name: string
    id: string
    label: string
    required?: boolean
    onChange: (value: string) => void
    size?: SizeType
}

export interface Task {
  name: string
  dueDate: string
  formatDueDate?: string
  priority: string
  doneDate: string
  state: boolean
  id: number
  key?: number
}