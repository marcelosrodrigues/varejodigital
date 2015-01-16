INSERT INTO telefone (id, ddd, telefone) VALUES (1, '021', '981363699');

INSERT INTO usuario (bloqueado, email, password, cpf, dataNascimento, bairro, cep, cidade, complemento, logradouro, numero, nomeCompleto, estado_id, celular_id)
  VALUE (0, 'marcelosrodrigues@globo.com', md5('12345678'), '070.323.277-02', '1977-08-17',
         'PECHINCHA', '22743-310', 'RIO DE JANEIRO', 'APTO 206', 'ESTRADA CAMPO DA AREIA', '84',
         'MARCELO DA SILVA RODRIGUES', 330, 1);

INSERT INTO telefone (id, ddd, telefone) VALUES (2, '021', '979910882')

INSERT INTO usuario (bloqueado, email, PASSWORD, cpf, dataNascimento, bairro, cep, cidade, complemento, logradouro, numero, nomeCompleto, estado_id)
VALUE (0, 'barbara@projetandoo.com.br', md5('12345678'), '', CURRENT_DATE(),
'PECHINCHA', '-', 'RIO DE JANEIRO', '-', '-', '-', 'BARBARA', 330);