
class SearchCodec {

	static encode = { q ->
		q = q.replace('á','a');
		q = q.replace('é','e');
		q = q.replace('í','i');
		q = q.replace('ó','o');
		q = q.replace('ú','u');
		q = q.replace('Á','A');
		q = q.replace('É','E');
		q = q.replace('Í','I');
		q = q.replace('Ó','O');
		q = q.replace('Ú','U');
		return q;
	}
}
