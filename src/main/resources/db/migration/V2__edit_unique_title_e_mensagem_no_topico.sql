ALTER table topico
ADD CONSTRAINT uk_topico_titulo UNIQUE (titulo);

ALTER table topico
ADD CONSTRAINT uk_topico_mensagem UNIQUE (mensagem);