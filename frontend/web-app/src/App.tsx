import { useEffect, useState } from 'react'
import { apiGet, apiMe, apiPost } from './api/client'
import { Card } from './components/Card'
import { Button } from './components/Button'
import { AgriIcon } from './components/AgriIcon'
import './styles/global.css'
import { Login } from './auth/Login'
import { clearAuth, isLoggedIn, loadAuth } from './auth/auth'

function App() {
  const [logged, setLogged] = useState(false)
  const [me, setMe] = useState<any | null>(null)

  useEffect(() => { loadAuth(); setLogged(isLoggedIn()) }, [])
  useEffect(() => { if (logged) { apiMe().then(setMe).catch(()=>{}) } }, [logged])

  if (!logged) return <Login onSuccess={() => setLogged(true)} />

  const isAdmin = !!me?.roles?.includes('ROLE_ADMIN') || me?.profile?.role === 'ADMIN'

  return (
    <div style={{ maxWidth: 980, margin: '0 auto', padding: 24 }}>
      <header style={{ display: 'flex', alignItems: 'center', padding: 18, background: '#e9f7df', borderRadius: 14, marginBottom: 20, boxShadow: '0 2px 8px #66bb6a22', justifyContent:'space-between' }}>
        <div style={{ display:'flex', alignItems:'center' }}>
          <AgriIcon size={44}/>
          <h2 style={{ margin: 0, marginLeft: 14, color: '#388e3c', fontWeight: 900, fontSize: 30 }}>VijayaSree Agri Retail</h2>
        </div>
        <div style={{ display:'flex', alignItems:'center', gap:12 }}>
          {me && <span style={{ color:'#388e3c', fontWeight:600 }}>{me.profile?.role || me.roles?.[0]}</span>}
          <Button onClick={() => { clearAuth(); setLogged(false) }}>Logout</Button>
        </div>
      </header>

      {isAdmin && (
        <section style={{ display:'flex', gap:16, marginBottom:18, flexWrap:'wrap' }}>
          <Card style={{ minWidth: 220 }}>
            <b style={{ color:'#388e3c' }}>Today’s Sales</b>
            <div style={{ color:'#6d4c41' }}>Coming soon: charts and totals.</div>
          </Card>
          <Card style={{ minWidth: 220 }}>
            <b style={{ color:'#388e3c' }}>Inventory Updates</b>
            <div style={{ color:'#6d4c41' }}>Purchase/returns & stock movements.</div>
          </Card>
        </section>
      )}

      <Products />
      <style>{`.card-hover:hover { border-color: #388e3c !important; box-shadow: 0 4px 18px 0 #66bb6a44 !important;}`}</style>
    </div>
  )
}

function Products() {
  const [rows, setRows] = useState<any[]>([])
  const [loading, setLoading] = useState(false)
  const [name, setName] = useState('Demo Product')
  const [id, setId] = useState('demo-ui-1')

  const load = async () => {
    setLoading(true)
    try { setRows(await apiGet('/api/products')) } finally { setLoading(false) }
  }
  useEffect(() => { load() }, [])

  return (
    <section>
      <div style={{ display:'flex', gap:8, marginBottom: 18 }}>
        <input placeholder="id" value={id} onChange={e=>setId(e.target.value)} />
        <input placeholder="name" value={name} onChange={e=>setName(e.target.value)} />
        <Button onClick={async ()=>{ await apiPost('/api/products', { id, name }); await load() }}>Add</Button>
        <Button onClick={load} disabled={loading}>{loading?'Loading...':'Refresh'}</Button>
      </div>
      <div style={{ display: 'flex', flexWrap: 'wrap', gap: 18 }}>
        {rows.map(r => (
          <Card key={r.id} style={{ minWidth: 200, flex: '1 1 200px' }}>
            <b style={{ color:'#388e3c', fontSize: '1.08em' }}>{r.name}</b>
            <div style={{ color:'#795548' }}>ID: {r.id}</div>
            {r.company?.name && <div style={{ color:'#4caf50', marginTop: 2 }}>Brand: {r.company.name}</div>}
            {r.volume && <span style={{ background:'#d0bc9c44', color:'#3e2723', padding:'1px 8px', borderRadius:8, fontSize:'0.95em', marginTop:6 }}>{r.volume}</span>}
            {r.price && <div style={{ color:'#c62828', fontSize:'1.03em', marginTop:4 }}>₹{r.price}</div>}
          </Card>
        ))}
      </div>
    </section>
  )
}

export default App
