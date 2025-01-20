import axios from "axios"
import { useEffect, useState } from "react"

function App() {
  const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"
  const [tasks, setTasks] = useState()

  useEffect(() => {
    const fetchData = async () => {
      const data = await axios.get(`${apiUrl}/todos?offset=10&limit=0`)
      console.log(data)
    }

    fetchData()
  }, [])

  return (

    

    <>
      <div className="h-screen w-screen p-4">
        <div className="border border-black w-full h-20 flex">


        </div>
      </div>

    </>
  )
}

export default App
