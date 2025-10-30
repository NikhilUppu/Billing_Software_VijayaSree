import { ReactNode } from 'react'
import { palette, spacing } from '../styles/theme'

export function Button({ children, onClick, style = {}, disabled }: { children: ReactNode, onClick?:()=>void, style?:any, disabled?:boolean }) {
  return <button
    style={{
      background: palette.green,
      color: palette.white,
      border: 'none',
      borderRadius: 22,
      padding: '8px 24px',
      marginRight: spacing.sm,
      fontWeight: 600,
      fontSize: '1rem',
      cursor: disabled ? 'not-allowed' : 'pointer',
      opacity: disabled ? 0.6 : 1,
      boxShadow: `0 1.5px 7px 0 ${palette.shadow}`,
      transition:'background 0.15s',
      ...style
    }}
    onClick={onClick}
    disabled={disabled}
    onMouseOver={e=>{ if(!disabled) e.currentTarget.style.background=palette.greenLight; }}
    onMouseOut={e=>{ if(!disabled) e.currentTarget.style.background=palette.green; }}
  >{children}</button>
}
