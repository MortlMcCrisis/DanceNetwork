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