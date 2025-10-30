const LS_KEY = 'vs_auth'

let authHeader: string | undefined

export function loadAuth() {
  try {
    const raw = localStorage.getItem(LS_KEY)
    if (!raw) return
    const { u, p } = JSON.parse(raw)
    setAuth(u, p)
  } catch {}
}

export function setAuth(u: string, p: string) {
  localStorage.setItem(LS_KEY, JSON.stringify({ u, p }))
  authHeader = 'Basic ' + btoa(`${u}:${p}`)
}

export function clearAuth() {
  localStorage.removeItem(LS_KEY)
  authHeader = undefined
}

export function getAuthHeader() {
  return authHeader
}

export function isLoggedIn() {
  return !!authHeader
}
