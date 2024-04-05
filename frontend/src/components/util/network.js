const API_BASE = '/api'
const API_VERSION_V1 = '/v1'

export const EVENTS_ENDPOINT = API_BASE + API_VERSION_V1 + '/events'
export const FILES_ENDPOINT = API_BASE + API_VERSION_V1 + '/files'
export const PAYMENTS_ENDPOINT = API_BASE + API_VERSION_V1 + '/payments'
export const NEWSFEED_ENTRIES_ENDPOINT = API_BASE + API_VERSION_V1 + '/newsfeed-entries'
export const TICKETS_ENDPOINT = API_BASE + API_VERSION_V1 + '/tickets'
export const TICKET_TYPES_ENDPOINT = API_BASE + API_VERSION_V1 + '/ticket-types'
export const USERS_ENDPOINT = API_BASE + API_VERSION_V1 + '/users'

export const fetchData = async (url, keycloakToken, setMethod) => {
  try {
    const response = await fetch(url, {
      headers: {
        Authorization: `Bearer ${keycloakToken}`,
        'Content-Type': 'application/json',
      },
    });
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const body = await response.json();

    setMethod(body);

    console.log(body);
  } catch (error) {
    console.error('Error fetching clients:', error);
  }
};

//TODO all methods should function in the same way
export const postData = async (url, keycloakToken, form) => {
  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${keycloakToken}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ ...form }),
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    return response;
  } catch (error) {
    console.error('Error posting data:', error);
  }
};

export const putData = async (url, keycloakToken, content) => {
  try {
    const response = await fetch(url, {
      method: 'PUT',
      headers: {
        Authorization: `Bearer ${keycloakToken}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({...content}),
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    return response;
  } catch (error) {
    console.error('Error putting data:', error);
  }
}