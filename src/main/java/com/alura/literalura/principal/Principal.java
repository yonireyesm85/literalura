package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.AutorDTO;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConversorLibro;
import com.alura.literalura.model.LibroDTO;

import java.util.*;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConversorLibro conversor = new ConversorLibro();
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            var menu = """
                    
                    ********** 📘 MENÚ LiterAlura 🦖 *************
                        📘📘💜😈🦖🦖🦖🦖🦖🦖🦖🦖😈💜📘📘 
                              
                    Elije una opción:

                    1 - Buscar libro por título
                    2 - Buscar libros por autor 
                    3 - Mostrar libros buscados
                    4 - Mostrar libros por idioma
                    5 - Mostrar autores buscados
                    6 - Mostrar autores vivos en un año específico
                    7 - Top 10 libros más descargados
                    8 - Mostrar estadísticas de descargas
                    🦖
                    
                    0 - Salir
                    """;
            System.out.println(menu);

            String entrada = scanner.nextLine();

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("❌ Opción no válida. 😈");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> buscarLibrosPorAutor();
                case 3 -> mostrarLibrosGuardados();
                case 4 -> mostrarLibrosPorIdioma();
                case 5 -> mostrarAutoresGuardados();
                case 6 -> mostrarAutoresVivosEnAnio();
                case 7 -> mostrarTop10LibrosMasDescargados();
                case 8 -> mostrarEstadisticasDeDescargas();

                case 0 -> System.out.println("¡Hasta pronto, Mi amigo Intelectual! 🦖");
                default -> System.out.println("❌ Opción no válida. 😈");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("🔍 Ingresa el título del libro: ");
        String titulo = scanner.nextLine().trim().replace(" ", "+");

        String url = "https://gutendex.com/books/?search=" + titulo;
        String json = consumoAPI.obtenerDatos(url);

        // Convertimos el JSON a DTO
        LibroDTO libroApi = conversor.convertirJsonEnLibroDesdeResultados(json);

        if (libroApi != null) {
            var autorDTO = libroApi.getAuthors().get(0);
            String nombreAutor = autorDTO.getName();
            Integer nacimiento = autorDTO.getBirth_year();
            Integer muerte = autorDTO.getDeath_year();
            String idioma = libroApi.getLanguages().isEmpty() ? "desconocido" : libroApi.getLanguages().get(0);

            // Buscar o crear autor (✅ corregido con Optional)
            Optional<Autor> optionalAutor = autorRepository.findByNombreIgnoreCase(nombreAutor);
            Autor autor = optionalAutor.orElseGet(() -> {
                Autor nuevoAutor = new Autor(nombreAutor, nacimiento, muerte);
                return autorRepository.save(nuevoAutor);
            });

            // Crear y guardar libro si no existe
            if (!libroRepository.existsById(libroApi.getId())) {
                Libro libro = new Libro(
                        libroApi.getId(),
                        libroApi.getTitle(),
                        idioma,
                        libroApi.getDownload_count(),
                        autor
                );
                libroRepository.save(libro);

                System.out.println("✅ Libro guardado correctamente:");
            }

            System.out.println("Título: " + libroApi.getTitle());
            System.out.println("Autor: " + nombreAutor);
            System.out.println("Idioma: " + idioma);
            System.out.println("Descargas: " + libroApi.getDownload_count());
        } else {
            System.out.println("❌ No se encontró el libro.");
        }
    }


    private void mostrarLibrosGuardados() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros guardados. 😵");
            return;
        }

        System.out.println("\n📚 Libros en el catálogo: 🦖📘📘📘");
        for (Libro libro : libros) {
            System.out.println("----------------------------");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getNumeroDeDescargas());
        }
    }

    private void mostrarLibrosPorIdioma() {
        System.out.print("🌐 Ingresa el código del idioma (ej: en, es, fr): ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros guardados en ese idioma.");
            return;
        }

        System.out.println("\n📚 Libros encontrados en idioma '" + idioma + "':");
        for (Libro libro : libros) {
            System.out.println("----------------------------");
            System.out.println("📗 Título: " + libro.getTitulo());
            System.out.println("👤 Autor: " + libro.getAutor().getNombre());
            System.out.println("⬇️  Descargas: " + libro.getNumeroDeDescargas());
        }
    }

    private void mostrarAutoresGuardados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("📭 No hay autores guardados.");
            return;
        }

        System.out.println("\n👤 Lista de autores:");
        for (Autor autor : autores) {
            System.out.println("----------------------------");
            System.out.println("👤 Nombre: " + autor.getNombre());
            System.out.println("📅 Nacimiento: " + autor.getAnioNacimiento());
            System.out.println("🪦 Fallecimiento: " + (autor.getAnioMuerte() != null ? autor.getAnioMuerte() : "¿Vive aún?"));
        }
    }

    private void mostrarAutoresVivosEnAnio() {
        System.out.print("🕰 Ingresa el año: ");
        int anio = Integer.parseInt(scanner.nextLine().trim());

        List<Autor> autoresVivos = autorRepository
                .findByAnioNacimientoLessThanEqualAndAnioMuerteGreaterThanEqual(anio, anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("❌ No se encontraron autores vivos en ese año.");
            return;
        }

        System.out.println("\n👤 Autores vivos en el año " + anio + ":");
        for (Autor autor : autoresVivos) {
            System.out.println("----------------------------");
            System.out.println("👤 Nombre: " + autor.getNombre());
            System.out.println("📅 Nacimiento: " + autor.getAnioNacimiento());
            System.out.println("🪦 Fallecimiento: " + autor.getAnioMuerte());
        }
    }

    private void buscarLibrosPorAutor() {
        System.out.print("🧑‍🎨 Ingresa el nombre del autor: ");
        String nombreAutor = scanner.nextLine().trim().replace(" ", "+");

        String url = "https://gutendex.com/books/?search=" + nombreAutor;
        String json = consumoAPI.obtenerDatos(url);

        List<LibroDTO> librosEncontrados = conversor.convertirJsonEnListaDeLibros(json);

        if (librosEncontrados.isEmpty()) {
            System.out.println("❌ No se encontraron libros de ese autor.");
            return;
        }

        System.out.println("📚 Libros encontrados:");
        for (LibroDTO dto : librosEncontrados) {
            // Tomar el primer autor
            AutorDTO autorDTO = dto.getAuthors().isEmpty() ? null : dto.getAuthors().get(0);
            if (autorDTO == null) continue;

            // Crear o encontrar autor
            Autor autor = autorRepository.findByNombreIgnoreCase(autorDTO.getName())
                    .orElseGet(() -> autorRepository.save(new Autor(
                            autorDTO.getName(),
                            autorDTO.getBirth_year(),
                            autorDTO.getDeath_year()
                    )));

            // Verificar si ya existe el libro
            if (libroRepository.existsById(dto.getId())) continue;

            // Crear y guardar libro
            Libro libro = new Libro(
                    dto.getId(),
                    dto.getTitle(),
                    dto.getLanguages().isEmpty() ? "desconocido" : dto.getLanguages().get(0),
                    dto.getDownload_count(),
                    autor
            );

            libroRepository.save(libro);

            // Mostrar en consola
            System.out.println("----------------------------");
            System.out.println("📗 Título: " + libro.getTitulo());
            System.out.println("👤 Autor: " + autor.getNombre());
            System.out.println("📚 Idioma: " + libro.getIdioma());
            System.out.println("⬇️  Descargas: " + libro.getNumeroDeDescargas());
        }

        System.out.println("✅ Todos los libros han sido procesados.");
    }

    private void mostrarTop10LibrosMasDescargados() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("❌ No hay libros guardados para analizar.");
            return;
        }

        System.out.println("\n🏆 Top 10 libros más descargados:\n");

        libros.stream()
                .sorted(Comparator.comparingInt(Libro::getNumeroDeDescargas).reversed())
                .limit(10)
                .forEach(libro -> {
                    System.out.println("📗 Título: " + libro.getTitulo());
                    System.out.println("👤 Autor: " + libro.getAutor().getNombre());
                    System.out.println("⬇️  Descargas: " + libro.getNumeroDeDescargas());
                    System.out.println("---------------------------");
                });
    }

    private void mostrarEstadisticasDeDescargas() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("❌ No hay libros en la base de datos.");
            return;
        }

        DoubleSummaryStatistics stats = libros.stream()
                .mapToDouble(Libro::getNumeroDeDescargas)
                .summaryStatistics();

        System.out.println("\n📊 Estadísticas de descargas:");
        System.out.println("📚 Total de libros: " + stats.getCount());
        System.out.println("⬇️  Total descargas: " + (int) stats.getSum());
        System.out.println("📈 Promedio de descargas: " + (int) stats.getAverage());
        System.out.println("📉 Mínimo de descargas: " + (int) stats.getMin());
        System.out.println("📊 Máximo de descargas: " + (int) stats.getMax());
    }

}
