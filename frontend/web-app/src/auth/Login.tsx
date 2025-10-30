import { useState } from 'react'
import { setAuth } from './auth'
import { apiGet } from '../api/client'
import { Button } from '../components/Button'
import { AgriIcon } from '../components/AgriIcon'

export function Login({ onSuccess }: { onSuccess: () => void }) {
  const [user, setUser] = useState('')
  const [pass, setPass] = useState('')
  const [remember, setRemember] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const submit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    setLoading(true)
    try {
      setAuth(user, pass)
      if (!remember) {
        try { localStorage.removeItem('vs_auth') } catch {}
      }
      await apiGet('/api/products')
      onSuccess()
    } catch (err: any) {
      setError(err?.message || 'Login failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={{ minHeight: '100vh', display:'flex', alignItems:'center', justifyContent:'center', padding: 24, background: 'linear-gradient(180deg, #e9f7df 0%, #f2eecb 60%, #ffffff 100%)' }}>
      <form onSubmit={submit} style={{ width: 420, maxWidth: '92vw', padding: 28, background:'#fff', borderRadius: 16, boxShadow:'0 12px 40px #00000018', border: '1px solid #e8e8e8', transition:'transform .15s, box-shadow .15s' }} className="login-card">
        <div style={{ display:'flex', alignItems:'center', gap:12, marginBottom: 18 }}>
          <AgriIcon size={40} />
          <div style={{ fontWeight: 800, fontSize: 22, color:'#388e3c' }}>VijayaSree</div>
        </div>
        <h1 style={{ margin:'6px 0 4px 0', fontSize: 36, color:'#2e7d32' }}>Sign In</h1>
        <div style={{ marginBottom: 18, color:'#5d4037' }}>Billing Software for Agricultural Business</div>

        <div style={{ display:'flex', flexDirection:'column', gap: 14 }}>
          <label style={{ fontWeight: 600, color:'#4e342e' }}>Mobile or Username</label>
          <input
            placeholder="e.g. 8985752079"
            value={user}
            onChange={e=>setUser(e.target.value)}
            style={{ padding:'12px 14px', borderRadius: 10, border:'1.5px solid #d9d9d9', outline:'none', transition:'border-color .15s, box-shadow .15s' }}
            onFocus={e=>{ e.currentTarget.style.borderColor='#66bb6a'; e.currentTarget.style.boxShadow='0 0 0 4px #66bb6a22' }}
            onBlur={e=>{ e.currentTarget.style.borderColor='#d9d9d9'; e.currentTarget.style.boxShadow='none' }}
          />

          <label style={{ fontWeight: 600, color:'#4e342e' }}>Password</label>
          <input
            placeholder="Password"
            type="password"
            value={pass}
            onChange={e=>setPass(e.target.value)}
            style={{ padding:'12px 14px', borderRadius: 10, border:'1.5px solid #d9d9d9', outline:'none', transition:'border-color .15s, box-shadow .15s' }}
            onFocus={e=>{ e.currentTarget.style.borderColor='#66bb6a'; e.currentTarget.style.boxShadow='0 0 0 4px #66bb6a22' }}
            onBlur={e=>{ e.currentTarget.style.borderColor='#d9d9d9'; e.currentTarget.style.boxShadow='none' }}
          />

          <div style={{ display:'flex', alignItems:'center', gap:10, marginTop: 4 }}>
            <input id="remember" type="checkbox" checked={remember} onChange={e=>setRemember(e.target.checked)} />
            <label htmlFor="remember" style={{ color:'#4e342e' }}>Remember me</label>
          </div>

          {error && <div style={{ color:'#c62828' }}>{error}</div>}
          <Button>{loading ? 'Signing in...' : 'Sign In'}</Button>

          <div style={{ marginTop: 6 }}>
            <a href="#" onClick={e=>e.preventDefault()} style={{ color:'#2e7d32', textDecoration:'none' }} onMouseOver={(e)=>{e.currentTarget.style.textDecoration='underline'}} onMouseOut={(e)=>{e.currentTarget.style.textDecoration='none'}}>
              Forgot password?
            </a>
            <div style={{ color:'#8d6e63', fontSize: 12, marginTop: 6 }}>Planned: OTP-based reset via mobile.</div>
          </div>
        </div>
      </form>
      <style>{`.login-card:hover{ transform: translateY(-2px); box-shadow: 0 16px 48px #00000022; }`}</style>
    </div>
  )
}
