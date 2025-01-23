import axios from "axios"
import { useEffect, useState } from "react"
import InputText from "./components/ui/InputText"
import InputSelect from "./components/ui/InputSelect"
import Buttons from "./components/ui/Buttons"
import { Task } from "./components/ui/types"
import { PaginationProps, Space, TableProps } from "antd"
import BaseTable from "./components/ui/BaseTable"
import { format } from "date-fns"
import BasePagination from "./components/ui/Pagination"

function App() {
  const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"
  const [tasks, setTasks] = useState<Task[]>([])
  const [formData, setFormData] = useState({ name: "", state: "all", priority: "all" })
  const [pages, setPages] = useState(1)
  const [page, setPage] = useState(1)

  const columns: TableProps<Task>['columns'] = [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Priority',
      dataIndex: 'priority',
      key: 'priority',
    },
    {
      title: 'Due date',
      dataIndex: 'dueDate',
      key: 'dueDate',
    },

    {
      title: 'Action',
      key: 'action',
      render: (_) => (
        <Space size="middle">
          <a>Edit </a>
          <a>Delete</a>
        </Space>
      ),
    },
  ]

  const fetchData = async () => {
    const data = await axios.get(`${apiUrl}/todos?page=${page}&limit=10&name=${formData.name}&state=${formData.state}&priority=${formData.priority}`)
    const list: Task[] = data.data.data
    list.forEach((element) => {
      element.key = element.id
      element.dueDate =  format(element.dueDate, 'dd/MM/yyyy');
    })
    setTasks(list)
    setPages(data.data.pages)
    console.log(tasks)
  }

  useEffect(() => {
    fetchData()
  }, [page])

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleChange = (value: string, name: string) => {
    setFormData({ ...formData, [name]: value })
  }

  const handleSubmit = async (e: React.FormEvent,) => {
    e.preventDefault()
    await fetchData()
  }

  const onPageChange: PaginationProps['onChange'] = async (page, pageSize) => {
    setPage(page)
    await fetchData()
  }

  return (
    <>
      <div className=" w-screen p-4">
        <form className="w-full flex flex-col p-4 gap-2" onSubmit={handleSubmit}>
          <InputText
            placeholder="Escribe aqui"
            name="name"
            id="name"
            type="text"
            size="large"
            label="Name"
            maxLength={120}
            showCount
            onChange={handleInputChange}
          />
          <InputSelect
            name="priority"
            label="Priority"
            id="priority"
            onChange={(value) => handleChange(value, "priority")}
            options={[
              { label: 'All', value: "all" },
              { label: 'High', value: "HIGH" },
              { label: 'Medium', value: "MEDIUM" },
              { label: 'Low', value: "LOW" },
            ]}
            defaultValue="all"
            size="large"
          />
          <InputSelect
            name="state"
            label="State"
            id="state"
            onChange={(value) => handleChange(value, "state")}
            options={[
              { label: 'All', value: "all" },
              { label: 'Done', value: "true" },
              { label: 'Undone', value: "false" },
            ]}
            defaultValue="all"
            size="large"
          />
          <Buttons htmlType="submit" text="Search" size="large" className="w-1/4 bg-purple-500 shadow" />
        </form>
        <BaseTable data={tasks} columns={columns} />
        <BasePagination onChange={onPageChange} current={page} total={pages} align={"center"} className={"mt-10"} />
        
      </div>
    </>
  )
}

export default App
