import axios from "axios"
import { useEffect } from "react"
import { useAppDispatch, useAppSelector } from "./app/hooks"
import { reloadRecords } from "./features/tasks/paginationSlice"
import { stateRecords, updateRecords } from "./features/tasks/taskSlice"
import DataViewer from "./components/todo/DataViewer"
import FilterForm from "./components/todo/FilterForm"
import BaseButton from "./components/ui/Buttons"
import CreationModal from "./components/todo/CreationModal"
import { controlCreate } from "./features/forms/modalSlice"
import { updateStats } from "./features/stats/statSlice"
import { Toaster } from "sonner"
import Statsviewer from "./components/todo/Statsviewer"

function App() {
  const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"

  const currentPage = useAppSelector(state => state.pagination.current)
  const filters = useAppSelector(state => state.filters)
  const modalCreate = useAppSelector(state => state.creation)
  const dispatch = useAppDispatch()

  const fetchData = async () => {
    const data = await axios.get(`${apiUrl}/todos?page=${currentPage}&limit=10&name=${filters.name}&state=${filters.state}&priority=${filters.priority}`)
    const stats = await axios.get(`${apiUrl}/todos/stats`)
    dispatch(updateStats(stats.data))
    dispatch(updateRecords(data.data.data))
    dispatch(reloadRecords(data.data.pages))
    dispatch(stateRecords(data.data.data))
  }

  useEffect(() => {
    fetchData()
  }, [currentPage])

  return (
    <div className="w-screen flex flex-col items-center">
      <h1 className="my-2 font-bold text-3xl">Todo App</h1>
      <FilterForm fetchData={fetchData} />
      <BaseButton onClick={() => dispatch(controlCreate(true))} text="New Todo" htmlType="button" />
      <DataViewer fetchData={fetchData} />
      {
        modalCreate &&
        <CreationModal fetchData={fetchData} />
      }
      <Statsviewer />
      <Toaster richColors visibleToasts={10} />
    </div>
  )
}

export default App
