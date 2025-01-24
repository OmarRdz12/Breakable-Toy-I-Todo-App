import axios from "axios"
import { useEffect } from "react"
import { useAppDispatch, useAppSelector } from "./app/hooks"
import { reloadRecords } from "./features/tasks/paginationSlice"
import { updateRecords } from "./features/tasks/taskSlice"
import DataViewer from "./components/todo/DataViewer"
import FilterForm from "./components/todo/FilterForm"
import BaseButton from "./components/ui/Buttons"
import CreationModal from "./components/todo/CreationModal"
import { controlCreate } from "./features/forms/modalSlice"

function App() {
  const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"

  const currentPage = useAppSelector(state => state.pagination.current)
  const filters = useAppSelector(state => state.filters)
  const dispatch = useAppDispatch()

  const fetchData = async () => {
    const data = await axios.get(`${apiUrl}/todos?page=${currentPage}&limit=10&name=${filters.name}&state=${filters.state}&priority=${filters.priority}`)
    dispatch(updateRecords(data.data.data))
    dispatch(reloadRecords(data.data.pages))
  }

  useEffect(() => {
    fetchData()
  }, [currentPage])

  return (

    <div className="w-screen p-4 box-border">
      <FilterForm fetchData={fetchData} />
      <BaseButton onClick={() => dispatch(controlCreate(true))} text="New Todo" htmlType="button" />
      <DataViewer fetchData={fetchData} />
      <CreationModal fetchData={fetchData} />
    </div>
  )
}

export default App
