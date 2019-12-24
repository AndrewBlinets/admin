    public String saveImage(@RequestParam("file") MultipartFile[] file) {
String name = null;
        for (MultipartFile multipartFile : file) {
            if (!multipartFile.isEmpty()) {
                try {
                    byte[] bytes = multipartFile.getBytes();
                    name = multipartFile.getOriginalFilename();
                    File dir = new File(ROOT_PATH + File.separator + "loadFiles");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                    stream.write(bytes);
                    stream.flush();
                    stream.close();
                    int width = 60;
                    InputStream in = new ByteArrayInputStream(bytes);
                    BufferedImage originalImage = ImageIO.read(in);
                    int height = originalImage.getHeight() / (originalImage.getWidth() / 60);
                    Image image = originalImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
                    BufferedImage changedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = changedImage.createGraphics();
                    g2d.drawImage(image, 0, 0, null);
                    g2d.dispose();
                    ImageIO.write(changedImage, "jpg", new File("C:\\Users\\blinec_a\\Desktop\\news-resize(4).jpg"));
//                logger.info("uploaded: " + uploadedFile.getAbsolutePath());
//                    return "You successfully uploaded file=" + name;
                } catch (Exception e) {
//                    return "You failed to upload " + name + " => " + e.getMessage();
                }
            } else {
                System.out.println("asdasd");
//                return "You failed to upload " + name + " because the file was empty.";
            }
        }
        return "asd";

            private static final String ROOT_PATH = "C:\\path\\";