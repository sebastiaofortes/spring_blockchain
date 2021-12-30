async function broadcast(data) {

data = 'data='+data;

// Para as requisições sejam recebidas é preciso fazer permitir os servidoes permitirem requests de seu domínio
// no php usamos:
// header("Access-Control-Allow-Origin: *"); para all domínios

var response = await fetch('https://blockchain7spring.herokuapp.com/localpow', {
	method: 'POST',
	body: data, // The data
	headers: {
		'Content-type': 'application/x-www-form-urlencoded' // The type of data you're sending
	}
})

return response

}
