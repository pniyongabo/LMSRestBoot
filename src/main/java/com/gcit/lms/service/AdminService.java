package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Publisher;

@CrossOrigin
@RestController
public class AdminService {
	@Autowired
	AuthorDAO adao;

	@Autowired
	BookDAO bdao;

	@Autowired
	GenreDAO gdao;

	@Autowired
	PublisherDAO pdao;

	@Autowired
	BranchDAO brdao;

	@Autowired
	BorrowerDAO bodao;

	@Autowired
	BookLoanDAO bldao;

	// ================================================================================
	// Books pages
	// ================================================================================

	@RequestMapping(value = "/api/books", method = RequestMethod.POST, consumes = "application/json")
	public Book addBook(@RequestBody Book book) throws SQLException { // create
																		// for
																		// POST
		Book newBook = bdao.readBookByPK(bdao.addBookWithID(book));
		return newBook;
	}

	@RequestMapping(value = "/api/books/{bookId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	// update for PUT
	public List<Book> editBook(@RequestBody Book book, @PathVariable Integer bookId) throws SQLException {
		book.setBookId(bookId);
		bdao.updateBook(book);
		return a_viewBooks(null, null);
	}

	// @RequestMapping(value = "/api/books", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Book> a_viewBooks() throws SQLException {
	// List<Book> books = bdao.readAllBooks();
	// for (Book b: books){
	// b.setAuthors(adao.readAllAuthorsByBookId(b.getBookId()));
	// b.setGenres(gdao.readAllGenresByBookId(b.getBookId()));
	// b.setPublisher(pdao.readPublisherByPubId(b.getBookId()));
	// }
	// return books;
	// }
	//
	// @RequestMapping(value = "/api/books", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Book> a_viewBooks(@RequestParam(value = "pageNo", required =
	// false) Integer pageNo) throws SQLException {
	// List<Book> books = bdao.readAllBooks(pageNo);
	// for (Book b: books){
	// b.setAuthors(adao.readAllAuthorsByBookId(b.getBookId()));
	// b.setGenres(gdao.readAllGenresByBookId(b.getBookId()));
	// b.setPublisher(pdao.readPublisherByPubId(b.getBookId()));
	// }
	// return books;
	// }

	@RequestMapping(value = "/api/books", method = RequestMethod.GET, produces = "application/json")
	public List<Book> a_viewBooks(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		if (searchString != null) {
			if (pageNo != null) {
				books = bdao.readAllBooksByName(pageNo, searchString);
			} else {
				books = bdao.readAllBooksByName(-1, searchString);
			}
		} else if (pageNo != null) {
			books = bdao.readAllBooks(pageNo);
		} else {
			books = bdao.readAllBooks();
		}

		for (Book b : books) {
			b.setAuthors(adao.readAllAuthorsByBookId(b.getBookId()));
			b.setGenres(gdao.readAllGenresByBookId(b.getBookId()));
			b.setPublisher(pdao.readPublisherByPubId(b.getBookId()));
		}
		return books;
	}

	@RequestMapping(value = "/api/books/{bookId}", method = RequestMethod.DELETE, consumes = "application/json ")
	public String deleteBook(@RequestBody Book book, @PathVariable Integer bookId) throws SQLException {
		book.setBookId(bookId);
		bdao.deleteBook(book);
		return "Book deleted successfully!";
	}

	// ================================================================================
	// Authors pages
	// ================================================================================

	/*
	 * @RequestMapping(value = "/api/authors", method = RequestMethod.POST,
	 * consumes="application/json") public String addAuthor(@RequestBody Author
	 * author) throws SQLException { adao.addAuthor(author); return
	 * "Author Added - Success is in the AIR!"; }
	 */

	// USEFUL FOR RETURNING A LOCATION HEADER TO THE ID OF THE NEW CREATED
	// RESOURCE
	@Transactional
	@RequestMapping(value = "/api/authors", method = RequestMethod.POST, consumes = "application/json")
	public Author addAuthor(@RequestBody Author author) throws SQLException {
		return adao.readAuthorByPK(adao.addAuthorWithID(author));
	}

	public Integer addAuthorWithID(@RequestBody Author author) throws SQLException {
		return adao.addAuthorWithID(author);
	}

	@RequestMapping(value = "/api/authors/{authorId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public List<Author> editBook(@RequestBody Author author, @PathVariable Integer authorId) throws SQLException {
		author.setAuthorId(authorId);
		adao.updateAuthor(author);
		return a_viewAuthors(null, null);
	}

	// @RequestMapping(value = "/api/authors", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Author> a_viewAuthors() throws SQLException {
	// List<Author> authors = adao.readAllAuthors();
	// for (Author a: authors){
	// a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
	// }
	// return authors;
	// }
	//
	// @RequestMapping(value = "/api/authors", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Author> a_viewAuthors(@RequestParam(value = "pageNo",
	// required = false) Integer pageNo) throws SQLException {
	// List<Author> authors = adao.readAllAuthors(pageNo);
	// for (Author a: authors){
	// a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
	// }
	// return authors;
	// }

	@RequestMapping(value = "/api/authors", method = RequestMethod.GET, produces = "application/json")
	public List<Author> a_viewAuthors(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) throws SQLException {
		List<Author> authors = new ArrayList<Author>();
		if (searchString != null) {
			if (pageNo != null) {
				authors = adao.readAllAuthorsByName(pageNo, searchString);
			} else {
				authors = adao.readAllAuthorsByName(-1, searchString);
			}
		} else if (pageNo != null) {
			authors = adao.readAllAuthors(pageNo);
		} else {
			authors = adao.readAllAuthors();
		}
		for (Author a : authors) {
			a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
		}
		return authors;
	}

	@RequestMapping(value = "/api/authors/{authorId}", method = RequestMethod.DELETE, consumes = "application/json ")
	public String deleteAuthor(@RequestBody Author author, @PathVariable Integer authorId) throws SQLException {
		author.setAuthorId(authorId);
		adao.deleteAuthor(author);
		return "Author deleted successfully!";
	}

	// ================================================================================
	// Borrowers pages
	// ================================================================================

	@Transactional
	@RequestMapping(value = "/api/borrowers", method = RequestMethod.POST, consumes = "application/json")
	public Borrower addBorrower(@RequestBody Borrower borrower) throws SQLException {
		Integer cardNo = bodao.addBorrowerWithID(borrower);
		Borrower newBorrower = bodao.readBorrowerByPK(cardNo);
		return newBorrower;
		// bodao.addBorrower(borrower);
		// return "borrower Added - Success is in the AIR!";
	}

	@Transactional
	@RequestMapping(value = "/api/borrowers/{cardNo}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public List<Borrower> editBook(@RequestBody Borrower borrower, @PathVariable Integer cardNo) throws SQLException {
		borrower.setCardNo(cardNo);
		bodao.updateBorrower(borrower);
		return a_viewborrowers(null, null);
	}

	// @RequestMapping(value = "/api/borrowers", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Borrower> a_viewborrowers() throws SQLException {
	// List<Borrower> borrowers = bodao.readAllBorrowers();
	// for (Borrower bo: borrowers){
	// bo.setBookLoans(bldao.readAllBookLoansByCardNo(bo.getCardNo()));
	// }
	// return borrowers;
	// }
	//
	// @RequestMapping(value = "/api/borrowers", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Borrower> a_viewborrowers(@RequestParam(value = "pageNo",
	// required = true) Integer pageNo) throws SQLException {
	// List<Borrower> borrowers = bodao.readAllBorrowers(pageNo);
	// for (Borrower bo: borrowers){
	// bo.setBookLoans(bldao.readAllBookLoansByCardNo(bo.getCardNo()));
	// }
	// return borrowers;
	// }

	@RequestMapping(value = "/api/borrowers", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> a_viewborrowers(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) throws SQLException {
		List<Borrower> borrowers = new ArrayList<Borrower>();
		if (searchString != null) {
			if (pageNo != null) {
				borrowers = bodao.readAllBorrowersByName(pageNo, searchString);
			} else {
				borrowers = bodao.readAllBorrowersByName(-1, searchString);
			}
		} else if (pageNo != null) {
			borrowers = bodao.readAllBorrowers(pageNo);
		} else {
			borrowers = bodao.readAllBorrowers();
		}
		for (Borrower bo : borrowers) {
			bo.setBookLoans(bldao.readAllBookLoansByCardNo(bo.getCardNo()));
		}
		return borrowers;
	}

	@Transactional
	@RequestMapping(value = "/api/borrowers/{cardNo}", method = RequestMethod.DELETE, consumes = "application/json ")
	public String deleteBorrower(@RequestBody Borrower borrower, @PathVariable Integer cardNo) throws SQLException {
		borrower.setCardNo(cardNo);
		bodao.deleteBorrower(borrower);
		return "borrower deleted successfully!";
	}

	// ================================================================================
	// Branches pages
	// ================================================================================

	@Transactional
	@RequestMapping(value = "/api/branches", method = RequestMethod.POST, consumes = "application/json")
	public Branch addBranch(@RequestBody Branch branch) throws SQLException {
		Integer branchId = brdao.addBranchWithID(branch);
		Branch newBranch = brdao.readBranchByPK(branchId);
		return newBranch;
		// return "branch Added - Success is in the AIR!";
	}

	@Transactional
	@RequestMapping(value = "/api/branches/{branchId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public List<Branch> editBranch(@RequestBody Branch branch, @PathVariable Integer branchId) throws SQLException {
		branch.setBranchId(branchId);
		brdao.updateBranch(branch);
		return a_viewbranches(null, null);
	}

	// @RequestMapping(value = "/api/branches", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Branch> a_viewbranches() throws SQLException {
	// List<Branch> branches = brdao.readAllBranches();
	// for (Branch br: branches){
	// br.setBooks(bdao.readAllBooksByBranchId(br.getBranchId()));
	// br.setBookLoans(bldao.readAllBookLoansByBranchId(br.getBranchId()));
	// }
	// return branches;
	// }
	//
	// @RequestMapping(value = "/api/branches", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Branch> a_viewbranches(@RequestParam(value = "pageNo",
	// required = false) Integer pageNo) throws SQLException {
	// List<Branch> branches = brdao.readAllBranches(pageNo);
	// for (Branch br: branches){
	// br.setBooks(bdao.readAllBooksByBranchId(br.getBranchId()));
	// br.setBookLoans(bldao.readAllBookLoansByBranchId(br.getBranchId()));
	// }
	// return branches;
	// }

	@RequestMapping(value = "/api/branches", method = RequestMethod.GET, produces = "application/json")
	public List<Branch> a_viewbranches(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) throws SQLException {
		List<Branch> branches = new ArrayList<Branch>();
		if (searchString != null) {
			if (pageNo != null) {
				branches = brdao.readAllBranchesByName(pageNo, searchString);
			} else {
				branches = brdao.readAllBranchesByName(-1, searchString);
			}
		} else if (pageNo != null) {
			branches = brdao.readAllBranches(pageNo);
		} else {
			branches = brdao.readAllBranches();
		}
		for (Branch br : branches) {
			br.setBooks(bdao.readAllBooksByBranchId(br.getBranchId()));
			br.setBookLoans(bldao.readAllBookLoansByBranchId(br.getBranchId()));
		}
		return branches;
	}

	@Transactional
	@RequestMapping(value = "/api/branches/{branchId}", method = RequestMethod.DELETE, consumes = "application/json ")
	public String deleteBranch(@RequestBody Branch branch, @PathVariable Integer branchId) throws SQLException {
		branch.setBranchId(branchId);
		brdao.deleteBranch(branch);
		return "branch deleted successfully!";
	}

	// ================================================================================
	// Publishers pages
	// ================================================================================

	@Transactional
	@RequestMapping(value = "/api/publishers", method = RequestMethod.POST, consumes = "application/json")
	public Publisher addPublisher(@RequestBody Publisher publisher) throws SQLException {
		Integer publisherId = pdao.addPublisherWithID(publisher);
		Publisher newPublisher = pdao.readPublisherByPubId(publisherId);
		return newPublisher;
	}

	@Transactional
	@RequestMapping(value = "/api/publishers/{publisherId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public List<Publisher> editPulisher(@RequestBody Publisher publisher, @PathVariable Integer publisherId) throws SQLException {
		publisher.setPublisherId(publisherId);
		pdao.updatePublisher(publisher);
		return a_viewpublishers(null, null);
	}

	// @RequestMapping(value = "/api/publishers", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Publisher> a_viewpublishers() throws SQLException {
	// List<Publisher> publishers = pdao.readAllPublishers();
	// for (Publisher p:publishers){
	// p.setBooks(bdao.readAllBooksByPublisherId(p.getPublisherId()));
	// }
	// return publishers;
	// }
	//
	// @RequestMapping(value = "/api/publishers", method = RequestMethod.GET,
	// produces="application/json")
	// public List<Publisher> a_viewpublishers(@RequestParam(value = "pageNo",
	// required = false) Integer pageNo) throws SQLException {
	// List<Publisher> publishers = pdao.readAllPublishers(pageNo);
	// for (Publisher p:publishers){
	// p.setBooks(bdao.readAllBooksByPublisherId(p.getPublisherId()));
	// }
	// return publishers;
	// }

	@RequestMapping(value = "/api/publishers", method = RequestMethod.GET, produces = "application/json")
	public List<Publisher> a_viewpublishers(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) throws SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		if (searchString != null) {
			if (pageNo != null) {
				publishers = pdao.readAllPublishersByName(pageNo, searchString);
			} else {
				publishers = pdao.readAllPublishersByName(-1, searchString);
			}
		} else if (pageNo != null) {
			publishers = pdao.readAllPublishers(pageNo);
		} else {
			publishers = pdao.readAllPublishers();
		}
		for (Publisher p : publishers) {
			p.setBooks(bdao.readAllBooksByPublisherId(p.getPublisherId()));
		}
		return publishers;
	}

	@Transactional
	@RequestMapping(value = "/api/publishers", method = RequestMethod.DELETE, consumes = "application/json ")
	public String deletePublisher(@RequestBody Publisher publisher) throws SQLException {
		pdao.deletePublisher(publisher);
		return "publisher deleted successfully!";
	}

	// ================================================================================
	// Loans pages
	// ================================================================================

	@Transactional
	@RequestMapping(value = "/api/bookloans/bookId/branchId/cardNo/dateOut", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public List<BookLoan> editBookLoan(@RequestBody BookLoan bookloan, @PathVariable Integer bookId, @PathVariable Integer branchId,
			@PathVariable Integer cardNo, @PathVariable String dateOut) throws SQLException {
		bookloan.setDateOut(dateOut);
		// may be pass date out in the body
		// this method is for overriding the due date
		bookloan.setBook(bdao.readBookByPK(bookId));
		bookloan.setBorrower(bodao.readBorrowerByPK(cardNo));
		bookloan.setBranch(brdao.readBranchByPK(branchId));
		bldao.updateBookLoan(bookloan);
		return a_viewbookloans(null, null);
	}

	// @RequestMapping(value = "/api/bookloans", method = RequestMethod.GET,
	// produces="application/json")
	// public List<BookLoan> a_viewbookloans() throws SQLException {
	// return bldao.readAllBookLoans();
	// }
	//
	// @RequestMapping(value = "/api/bookloans", method =
	// RequestMethod.GET, produces="application/json")
	// public List<BookLoan> a_viewbookloans(@RequestParam(value = "pageNo",
	// required = false) Integer pageNo) throws SQLException {
	// return bldao.readAllBookLoans(pageNo);
	// }

	@RequestMapping(value = "/api/bookloans", method = RequestMethod.GET, produces = "application/json")
	public List<BookLoan> a_viewbookloans(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) throws SQLException {
		if (searchString != null) {
			if (pageNo != null) {
				return bldao.readAllBookLoansByDateOut(pageNo, searchString);
			} else {
				return bldao.readAllBookLoansByDateOut(-1, searchString);
			}
		} else if (pageNo != null) {
			return bldao.readAllBookLoans(pageNo);
		} else {
			return bldao.readAllBookLoans();
		}
	}
}
