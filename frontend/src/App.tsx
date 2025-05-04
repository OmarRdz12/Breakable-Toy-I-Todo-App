// App.tsx
import { useEffect } from "react"
import { useAppDispatch, useAppSelector } from "./app/hooks"
import { controlCreate } from "./features/forms/modalSlice"
import { Toaster } from "sonner"
import { IoMdAddCircle } from "react-icons/io"

import { useFetchTodos } from "./hooks/useFetchTodos"

import FilterForm from "./components/todo/FilterForm"
import DataViewer from "./components/todo/DataViewer/DataViewer"
import CreationModal from "./components/todo/CreationModal"
import StatsViewer from "./components/todo/StatsViewer/StatsViewer"
import BaseButton from "./components/ui/Buttons/Buttons"
import { useGreeting } from "./hooks/useGreeting"

function App() {
  const dispatch = useAppDispatch()
  const modalCreate = useAppSelector(state => state.creation)
  const pendingTasks = useAppSelector(state => state.pending)
  const sorts = useAppSelector(state => state.sorts)
  const currentPage = useAppSelector(state => state.pagination.current)

  const { fetchTodos } = useFetchTodos()
  const greeting = useGreeting()

  useEffect(() => {
    fetchTodos()
  }, [currentPage, sorts])

  return (
    <div className="flex flex-col items-center">
      <div className="w-11/12 h-fit p-4">
        <h1 className="text-left text-xl text-gray-500">{greeting}</h1>
        <h2 className="text-left text-2xl font-bold">You have <span className="text-blue-400">{pendingTasks}</span> pending tasks</h2>
      </div>
      <FilterForm />
      <div className="w-11/12 flex items-start">
        <BaseButton
          onClick={() => dispatch(controlCreate(true))}
          text="New Todo"
          htmlType="button"
          size="large"
          icon={<IoMdAddCircle />}
          className="bg-blue-400 text-black hover:!bg-blue-500 
           hover:!border-blue-500 hover:!text-black border border-black"
        />
      </div>
      <DataViewer fetchData={fetchTodos} />
      {modalCreate && <CreationModal />}
      <StatsViewer />
      <Toaster richColors visibleToasts={10} />
    </div>
  )
}

export default App
