import { Checkbox, CheckboxProps } from "antd"
import axios from "axios"
import { useState } from "react"
import { useAppDispatch, useAppSelector } from "../../app/hooks"
import { updateStateRecords } from "../../features/tasks/taskSlice"

interface BaseCheckboxProps {
    originChecked?: boolean
    id?: number
    fetchData(): Promise<void>
    columnSelector?: boolean
    setChecked: ((id: number, state: boolean) => void) | (() => void)
}

const BaseCheckbox = ({ originChecked = false, id, fetchData, columnSelector, setChecked }: BaseCheckboxProps) => {
    const stateRecords = useAppSelector(state => state.stateTask)
    const tasks = useAppSelector(state => state.tasks.data)
    const [disabled, setDisabled] = useState(false)
    const dispatch = useAppDispatch()
    const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"

    const onChange: CheckboxProps['onChange'] = async (e) => {
        try {
            if (columnSelector) {
                dispatch(updateStateRecords(stateRecords))
            } else {
                id && setChecked(id, e.target.checked)
            }
            setDisabled(true)
            if (columnSelector) {
                tasks.forEach(async (element) => {
                    if (element.state === stateRecords) {
                        if (stateRecords) {
                            const stats = await axios.put(`${apiUrl}/todos/${element.id}/undone`)
                        } else {
                            const stats = await axios.put(`${apiUrl}/todos/${element.id}/done`)
                        }
                    }
                })
            }
            else {
                if (originChecked) {
                    const stats = await axios.put(`${apiUrl}/todos/${id}/undone`)
                } else {
                    const stats = await axios.put(`${apiUrl}/todos/${id}/done`)
                }
            }
            await fetchData()
        } catch (error) {
            console.log(error)
        } finally {
            setDisabled(false)
        }
    }
    return (
        <>
            {
                columnSelector ?
                    <Checkbox checked={stateRecords} disabled={disabled} onChange={onChange} /> :
                    <Checkbox checked={originChecked} disabled={disabled} onChange={onChange} />
            }
        </>
    )
}

export default BaseCheckbox