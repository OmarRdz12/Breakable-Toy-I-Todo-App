import { useEffect, useState } from "react"

function getGreeting(): string {
  const now = new Date()
  const hour = now.getHours()

  if (hour >= 5 && hour < 12) return "Good morning!"
  if (hour >= 12 && hour < 18) return "Good afternoon!"
  if (hour >= 18 && hour < 22) return "Good evening!"
  return "Good night!"
}

export function useGreeting() {
  const [greeting, setGreeting] = useState(getGreeting())

  useEffect(() => {
    const interval = setInterval(() => {
      setGreeting(getGreeting())
    }, 3600000)

    return () => clearInterval(interval)
  }, [])

  return greeting
}

