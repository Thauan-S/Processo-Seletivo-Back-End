package com.clinica.api;

import com.clinica.api.model.Consulta;
import com.clinica.api.model.Paciente;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(){
		return args-> {

			List<Paciente> pacienteList = new ArrayList();
			List<Consulta> consultasList =new ArrayList();


			Scanner scanner = new Scanner(System.in);

			int opcao;
			mostrarMenu();
			opcao=scanner.nextInt();
			do{

				if (opcao==1){
					scanner.nextLine();
				Paciente paciente = new Paciente();
				System.out.println("Digite o nome  do usuário");
				paciente.setNome(scanner.nextLine());
				System.out.println("Digite o telefone  do usuário");
				paciente.setTelefone(scanner.nextLine());
					boolean jaCadastrado=true;
					for (Paciente p:pacienteList){
						if(p.getTelefone().equals(paciente.getTelefone())){

							System.out.println("Paciente já cadastrado!");
							jaCadastrado=false;
							mostrarMenu();
							opcao=scanner.nextInt();
						}
					}
					if(jaCadastrado){
					pacienteList.add(paciente);
					System.out.println("Paciente cadastrado com sucesso");
					mostrarMenu();
					opcao=scanner.nextInt();
					}


				} else if (opcao==2) {
					for (int i = 0; i < pacienteList.size(); i++) {
						System.out.println("paciente numero "+i+":"+pacienteList.get(i).getNome());
					}
					System.out.println("escolha o numero de um paciente");
					int numeroPaciente=scanner.nextInt();
					Consulta consulta = new Consulta();
						consulta.setPaciente(pacienteList.get(numeroPaciente));
						System.out.println("escolha o dia do atendimento");
						consulta.setDia(scanner.nextInt());
						System.out.println("escolha a hora do atendimento");
						scanner.nextLine();
						consulta.setHora(scanner.nextLine());
						System.out.println("digite a especialidade  para a consulta");
						consulta.setEspecialidade(scanner.nextLine());

						boolean estaDisponivel=true;
						var dataAtual= Instant.now();
						var dataZona=dataAtual.atZone(ZoneId.of("UTC"));
					    LocalDate dataLocal=dataZona.toLocalDate();
						var diaAtual=dataLocal.getDayOfMonth();

					for(Consulta c:consultasList) {

							if (consulta.getDia()==c.getDia()&& consulta.getHora().equals(c.getHora())){

								System.out.println("Já existe uma consulta marcada para este dia neste horário, por favor, escolha outro horário");
								estaDisponivel=false;
								mostrarMenu();
								opcao=scanner.nextInt();
							}

					}
					if(consulta.getDia() < diaAtual){
						System.out.println("Consultas não podem ser marcadas antes da data atual");
						estaDisponivel=false;
						mostrarMenu();
						opcao=scanner.nextInt();
					}
					if (estaDisponivel) {
						consultasList.add(consulta);
						System.out.println("Consulta adicionada com sucesso");
						mostrarMenu();
						opcao=scanner.nextInt();
					}
				} else if (opcao==3) {
					int i=0;
					for (Consulta c:consultasList){
						System.out.println("consulta: "+i+" paciente: "+c.getPaciente().getNome()+" escpecialidade: "+c.getEspecialidade());
						i++;
					}
					System.out.println("Escolha a colsulta que deseja cancelar");
					consultasList.remove(scanner.nextInt());
					System.out.println("Colsulta cancelada com sucesso");
					mostrarMenu();
					opcao=scanner.nextInt();
				}
				//mostrarMenu();
				//opcao=scanner.nextInt();


			}while (opcao!=0);
			scanner.close();
			for (Consulta c : consultasList){
				System.out.println("consulta "+c.getDia()+" "+c.getEspecialidade()+" "+c.getHora()+" "+c.getPaciente());
			}
			System.out.println("tamanho da lista: "+consultasList.size());
		};

		}
	public static void mostrarMenu() {
		System.out.println("escolha 1 para cadastrar um paciente");
		System.out.println("escolha 2 para marcar uma consulta");
		System.out.println("escolha 3 para cancelar uma consulta");
	}
}
