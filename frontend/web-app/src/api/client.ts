import { getAuthHeader } from '../auth/auth'

function baseHeaders() {
  const h: Record<string,string> = {}
  const a = getAuthHeader()
  if (a) h['Authorization'] = a
  return h
}

export async function apiGet(path: string) {
  const res = await fetch(path, { headers: baseHeaders() })
  if (res.status === 401) throw new Error('Unauthorized')
  if (!res.ok) throw new Error(`GET ${path} failed: ${res.status}`)
  return res.json()
}

export async function apiPost(path: string, body: unknown) {
  const res = await fetch(path, {
    method: 'POST',
    headers: { ...baseHeaders(), 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  })
  if (res.status === 401) throw new Error('Unauthorized')
  if (!res.ok) throw new Error(`POST ${path} failed: ${res.status}`)
  return res.json()
}

export async function apiMe() {
  return apiGet('/api/me')
}
