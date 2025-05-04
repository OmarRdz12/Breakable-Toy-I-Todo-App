import axios from "axios"
import { useCallback } from "react"
import { useAppDispatch, useAppSelector } from "../app/hooks"
import { updateRecords, stateRecords } from "../features/tasks/taskSlice"
import { reloadRecords } from "../features/tasks/paginationSlice"
import { updateStats } from "../features/stats/statSlice"
import { updatePending } from "../features/stats/pendingSlice"
import { toast } from "sonner"

export function useFetchTodos() {
  const dispatch = useAppDispatch()
  const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"

  const currentPage = useAppSelector(state => state.pagination.current)
  const filters = useAppSelector(state => state.filters)
  const sorts = useAppSelector(state => state.sorts)

  const fetchTodos = useCallback(async () => {
    try {
      const url = `${apiUrl}/todos?page=${currentPage}&limit=10&name=${encodeURIComponent(filters.name)}&state=${filters.state}&priority=${filters.priority}&dueDateSort=${sorts.dueDateSort}&prioritySort=${sorts.prioritySort}`

      const [tasksRes, statsRes, pendingRes] = await Promise.all([
        axios.get(url),
        axios.get(`${apiUrl}/todos/stats`),
        axios.get(`${apiUrl}/todos/pending`)
      ])

      dispatch(updateRecords(tasksRes.data.data))
      dispatch(stateRecords(tasksRes.data.data))
      dispatch(reloadRecords(tasksRes.data.pages))
      dispatch(updateStats(statsRes.data))
      dispatch(updatePending(pendingRes.data))
    } catch (error) {
      toast.error("Something went wrong with the server")
    }
  }, [apiUrl, currentPage, filters, sorts, dispatch])

  return { fetchTodos }
}
