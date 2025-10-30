import { ReactNode } from 'react'
import { palette, spacing } from '../styles/theme'

export function Card({ children, style = {} }: { children: ReactNode, style?: any }) {
  return <div
    style={{
      background: palette.white,
      borderRadius: 12,
      boxShadow: `0 2px 10px 0 ${palette.shadow}`,
      border: `1.5px solid ${palette.muted}`,
      padding: spacing.md,
      marginBottom: spacing.md,
      transition: 'box-shadow 0.15s, border-color 0.15s',
      cursor: 'pointer',
      ...style
    }}
    className="card-hover"
  >{children}</div>
}

// --- CSS for hover effect ---
// In App or index, inject:
// .card-hover:hover { border-color: #388e3c !important; box-shadow: 0 4px 16px 0 #66bb6a44 !important;}
