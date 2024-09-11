import React from 'react';

function Ticket() {
  return (
      <div
          style={{
            fontFamily: 'Arial, sans-serif',
            backgroundColor: '#f5f5f5',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '100vh',
            margin: 0,
          }}
      >
        <div
            style={{
              display: 'flex',
              borderRadius: '15px',
              overflow: 'hidden',
              width: '1000px',
              maxWidth: '100%',
              backgroundColor: 'var(--bs-primary)',
            }}
        >
          {/* Container für die Ticketpreise */}
          <div
              style={{
                display: 'flex',
                width: '40%',
              }}
          >
            {/* Early Bird Ticket */}
            <div
                style={{
                  display: 'flex',
                  flexDirection: 'column',
                  justifyContent: 'center',
                  alignItems: 'center',
                  color: 'white',
                  padding: '20px',
                  backgroundColor: 'var(--bs-success)',
                  width: '20%', // Breite auf 1/3 des Containers setzen
                  textAlign: 'center',
                }}
            >
              <div style={{ fontSize: '36px', fontWeight: 'bold', writingMode: 'sideways-lr', textDecoration: 'line-through' }}>399 €</div>
              <div style={{ fontSize: '16px', fontWeight: 'bold', marginTop: '10px', writingMode: 'sideways-lr', textDecoration: 'line-through' }}>
                EARLY BIRD TICKET<br/>
                <span style={{ fontSize: '14px', marginTop: '5px', color: '#d4edcf' }}>SOLD OUT</span>
              </div>
            </div>

            {/* Standard Ticket */}
            <div
                style={{
                  display: 'flex',
                  flexDirection: 'column',
                  justifyContent: 'center',
                  alignItems: 'center',
                  color: 'white',
                  padding: '20px',
                  backgroundColor: 'var(--bs-warning)',
                  width: '20%', // Breite auf 1/3 des Containers setzen
                  textAlign: 'center',
                }}
            >
              <div style={{ fontSize: '36px', fontWeight: 'bold', writingMode: 'sideways-lr', textDecoration: 'line-through' }}>489 €</div>
              <div style={{ fontSize: '16px', fontWeight: 'bold', marginTop: '10px', writingMode: 'sideways-lr', textDecoration: 'line-through' }}>
                STANDARD TICKET<br/>
              <span style={{ fontSize: '14px', marginTop: '5px' }}>SOLD OUT</span>
              </div>
            </div>

            {/* Late Bird Ticket */}
            <div
                style={{
                  display: 'flex',
                  flexDirection: 'column',
                  justifyContent: 'center',
                  alignItems: 'center',
                  color: 'white',
                  padding: '20px',
                  backgroundColor: 'var(--bs-primary)',
                  width: '20%', // Breite auf 1/3 des Containers setzen
                  textAlign: 'center'
                }}
            >
              <div style={{ fontSize: '36px', fontWeight: 'bold', writingMode: 'sideways-lr'}}>579 €</div>
              <div style={{ fontSize: '16px', fontWeight: 'bold', marginTop: '10px', writingMode: 'sideways-lr'}}>
                LATE BIRD TICKET
                <br/>
                <span style={{ fontSize: '14px', marginTop: '5px', color: '#d4edcf' }}>until 27.01</span>
              </div>
            </div>

            {/*aktuelles ticket*/}
            <div
                style={{
                  display: 'flex',
                  flexDirection: 'column',
                  justifyContent: 'center',
                  alignItems: 'center',
                  color: 'white',
                  padding: '20px',
                  backgroundColor: 'var(--bs-primary)',
                  width: '40%', // Breite auf 1/3 des Containers setzen
                  textAlign: 'center',
                  borderLeft: '1px dashed white',
                  borderRight: '1px dashed white'
                }}
            >
              <div style={{ fontSize: '36px', fontWeight: 'bold'}}>579 €</div>
              <div style={{ fontSize: '16px', fontWeight: 'bold', marginTop: '10px'}}>
                LATE BIRD TICKET<br/>
              </div>
            </div>
          </div>

          {/* Rechte Spalte */}
          <div
              style={{
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                width: '60%',
                padding: '20px',
              }}
          >
            <div style={{ textAlign: 'center', color: 'white' }}>
              <h2 style={{ fontSize: '32px', marginBottom: '20px', color: 'white' }}>
                Bachata Zouk Royals
              </h2>
              <img src="/qrcodes/code1.png" alt="mimg"
                   className="avatar job-icon mb-2 d-inline-block"
                   loading="lazy"/>
              <br/>
              <br/>
            </div>
            <div
                style={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  color: 'white',
                  fontSize: '14px',
                  borderTop: '1px solid white',
                  paddingTop: '10px',
                }}
            >
              <div>
                <div>Katowice, Poland</div>
                <div>plac Sławika i Antalla 1, 40-163</div>
              </div>
              <div>
                <div>3-day festival</div>
                <div>August 16, 2024 - August 18, 2024</div>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}

export default Ticket;
