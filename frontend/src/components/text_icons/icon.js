const Icon = ({symbol, text}) => {

  return (
      <div className="d-flex align-items-center mb-1">
        <span className="material-symbols-outlined md-18">{symbol}</span>
        <span className="ms-2">{text}</span>
      </div>
  )
}

export default Icon